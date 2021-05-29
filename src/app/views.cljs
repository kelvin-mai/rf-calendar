(ns app.views
  (:require [re-frame.core :as rf]
            [app.components.ui :as ui]
            [app.components.calendar :as calendar]
            [app.components.event-form :as event-form]))

(defn app []
  (let [text @(rf/subscribe [:text])
        form-values @(rf/subscribe [:event/form])]
    [ui/container
     [:<>
      [ui/sidebar 
       [event-form/event-form form-values]]
      [:h2 text]
      [calendar/calendar]]]))
