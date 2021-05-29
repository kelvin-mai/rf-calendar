(ns app.db)

(def initial-db
  {:text "Hello world!"
   :ui {:theme "light"
        :sidebar-open? true}
   :calendar {:events [{:title "Example Event"
                        :start (js/Date.)
                        :end (js/Date.)
                        :all-day false}]}})
