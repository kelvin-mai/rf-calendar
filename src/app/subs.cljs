(ns app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :text
 (fn [db] (:text db)))
