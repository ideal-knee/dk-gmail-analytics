DK Gmail Analytics
==================

Find out which email addresses emailed you the most last week.

Usage
-----

  1. Install [Java](https://java.com/en/download/) and [Leiningen](https://leiningen.org/#install)

  2. Somehow get a hold of a Gmail API client ID, secret, and refresh token

    * Maybe using [this](https://github.com/mikeflynn/gmail-clj/blob/master/resources/index.html) or
      [this](https://developers.google.com/gmail/api/auth/about-auth)

3. Save your auth stuff in an EDN file

         {:client-id "<client-id>"
          :client-secret "<client-secret>"
          :refresh-token "<refresh-token>" }

  4. `lein run <path-to-config-file>`

License
-------

Copyright © 2017 Dan Kee

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
