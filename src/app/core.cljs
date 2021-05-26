(ns app.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [app.events]
            [app.subs]
            [app.views :as views]))

(defn ^:dev/after-load mount []
  (rf/clear-subscription-cache!)
  (rdom/render [views/app]
               (.getElementById js/document "app")))

(defn ^:export init []
  (rf/dispatch-sync [:initialize-db])
  (mount))

