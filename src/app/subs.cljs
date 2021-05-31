(ns app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :text
 (fn [db] (:text db)))

(rf/reg-sub
 :ui/theme
 (fn [db]
   (get-in db [:ui :theme])))

(rf/reg-sub
 :ui/sidebar-open?
 (fn [db]
   (get-in db [:ui :sidebar-open?])))

(rf/reg-sub
 :calendar/events
 (fn [db]
   (get-in db [:calendar :events])))

(rf/reg-sub
 :event/form
 (fn [db]
   (get-in db [:event :form])))

(rf/reg-sub
 :event/mode
 (fn [db]
   (get-in db [:event :mode])))
