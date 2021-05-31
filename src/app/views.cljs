(ns app.views
  (:require [app.components.ui :as ui]
            [app.components.calendar :as calendar]
            [app.components.event-form :as event-form]))

(defn app []
  [ui/container
   [:<>
    [ui/sidebar
     [event-form/event-form]]
    [calendar/calendar]]])
