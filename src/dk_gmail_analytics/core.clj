(ns dk-gmail-analytics.core
  (:require [clojure.edn :as edn]
            [clojure.pprint :refer [pprint]]
            [clj-time.core :as time]
            [clj-time.format :as tf]
            [gmail-clj.core :as gmail] )
  (:gen-class) )

(defn init [{:keys [client-id client-secret refresh-token]}]
  (gmail/set-client-id! client-id)
  (gmail/set-client-secret! client-secret)
  (gmail/set-refresh-token! refresh-token) )

(def gmail-date-formatter (tf/formatter "yyyy/MM/dd"))

(defn message-query []
  (let [last-monday (.withDayOfWeek (time/now) 1)
        monday-before-last (time/minus last-monday (time/weeks 1)) ]
    (str "after:"
         (tf/unparse gmail-date-formatter monday-before-last)
         " before:"
         (tf/unparse gmail-date-formatter last-monday) ) ) )

(defn -main [config-file]
  (init (->> config-file
             slurp
             edn/read-string ))

  (let [{:keys [messages nextPageToken]} (gmail/message-list (message-query) :max-results 1000)]
    (if nextPageToken
      (println "NEED TO BUMP MAX RESULTS")
      (->> messages
           (map (fn [message]
                  (->> message
                       :id
                       gmail/message-get
                       :payload
                       :headers
                       (filter #(= (:name %) "From"))
                       first
                       :value ) ))
           frequencies
           (sort-by #(- (second %)))
           (take 50)
           pprint ) ) ) )
