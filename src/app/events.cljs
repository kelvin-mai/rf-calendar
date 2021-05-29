(ns app.events
  (:require [re-frame.core :as rf]
            [app.utils.dates :refer [create-datetime
                                     new-event]]
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

(rf/reg-event-db
 :event-form/update-field
 (fn [db [_ k v]]
   (assoc-in db [:event :form k] v)))

(rf/reg-event-fx
 :event-form/init-create
 (fn [{:keys [db]} [_ date]]
   {:db (-> db
            (assoc-in [:event :form] (new-event date))
            (assoc-in [:event :edit] :create))
    :dispatch [:ui/toggle-sidebar]}))

(rf/reg-event-fx
 :event-form/submit-create
 (fn [{:keys [db]} [_]]
   (let [{:keys [date start end] :as values} (get-in db [:event :form])
         values (assoc values
                       :start (create-datetime date start)
                       :end (create-datetime date end))
         events (get-in db [:calendar :events])
         events (conj events values)]
     {:db (-> db
              (assoc-in [:calendar :events] events))
      :dispatch [:ui/toggle-sidebar]})))

(rf/reg-event-fx
  :event-form/init-update
  (fn [{:keys [db]} [_]]
    {:db db
     :dispatch [:ui/toggle-sidebar]}))
