(ns app.events
  (:require [re-frame.core :as rf]
            [app.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn []
   db/initial-db))

(rf/reg-event-db
 :ui/toggle-theme
 (fn [db]
   (let [theme (get-in db [:ui :theme])
         theme (if (= theme "dark")
                 "light"
                 "dark")]
     (assoc-in db [:ui :theme] theme))))

(rf/reg-event-db
 :ui/toggle-sidebar
 (fn [db]
   (assoc-in db [:ui :sidebar-open?]
             (not (get-in db [:ui :sidebar-open?])))))

