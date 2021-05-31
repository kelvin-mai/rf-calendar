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
            (assoc-in [:event :mode] :create))
    :dispatch [:ui/toggle-sidebar]}))

(rf/reg-event-fx
 :event-form/submit-create
 (fn [{:keys [db]}]
   (let [{:keys [date start end] :as values} (get-in db [:event :form])
         values (assoc values
                       :id (swap! db/id-cache inc)
                       :start (create-datetime date start)
                       :end (create-datetime date end))
         events (get-in db [:calendar :events])
         events (conj events values)]
     {:db (-> db
              (assoc-in [:calendar :events] events))
      :dispatch [:ui/toggle-sidebar]})))

(rf/reg-event-fx
 :event-form/init-edit
 (fn [{:keys [db]} [_ id]]
   (let [events (get-in db [:calendar :events])
         selected (->> events
                       (filter #(= id (:id %)))
                       (first))]
     {:db (-> db
              (assoc-in [:event :form] selected)
              (assoc-in [:event :mode] :edit))
      :dispatch [:ui/toggle-sidebar]})))

(rf/reg-event-fx
 :event-form/submit-edit
 (fn [{:keys [db]}]
   (let [{:keys [date start end] :as values} (get-in db [:event :form])
         selected-id (:id values)
         values (assoc values
                       :start (create-datetime date start)
                       :end (create-datetime date end))
         events (get-in db [:calendar :events])
         events (map
                 #(if (= selected-id (:id %))
                    values
                    %)
                 events)]
     {:db (assoc-in db [:calendar :events] events)
      :dispatch [:ui/toggle-sidebar]})))

(rf/reg-event-fx
 :event-form/delete
 (fn [{:keys [db]}]
   (let [{:keys [id]} (get-in db [:event :form])
         events (get-in db [:calendar :events])
         events (filter #(not (= id (:id %))) events)]
     {:db (assoc-in db [:calendar :events] events)
     :dispatch [:ui/toggle-sidebar]})))
