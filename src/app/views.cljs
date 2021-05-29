(ns app.views
  (:require [re-frame.core :as rf]
            [app.components.ui :as ui]
            [app.components.calendar :as calendar]))

(defn app []
  (let [text @(rf/subscribe [:text])]
    [ui/container
     [:<>
      [ui/sidebar 
       [calendar/event-form]]
      [:h2 text]
      [calendar/calendar]]]))
