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
