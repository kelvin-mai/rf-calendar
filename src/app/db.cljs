(ns app.db
  (:require [reagent.core :as r]))

(defonce id-cache
  (r/atom 0))

(def initial-db
  {:text "Re-Frame Calendar"
   :ui {:theme "light"
        :sidebar-open? false}
   :calendar {:events []}
   :event {:form nil}})

(comment 
  (reset! id-cache 0)
  (swap! id-cache inc))
