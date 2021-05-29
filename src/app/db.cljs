(ns app.db)

(def initial-db
  {:text "Hello world!"
   :ui {:theme "light"
        :sidebar-open? true}
   :calendar {:events []}
   :event {:form nil}})
