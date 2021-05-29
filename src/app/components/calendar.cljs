(ns app.components.calendar
  (:require [re-frame.core :as rf]
            ["@material-ui/core" :refer [Paper]]
            ["react-big-calendar" :refer [Calendar
                                          momentLocalizer]]
            ["moment" :as moment]))

(defn calendar []
  (let [events @(rf/subscribe [:calendar/events])]
    [:> Paper {:style {:background-color "white"
                       :color "black"
                       :padding "1rem"}}
     [:> Calendar {:localizer (momentLocalizer moment)
                   :events events
                   :views ["month" "week" "day"]
                   :style {:height "80vh"}
                   :selectable true
                   :on-select-event #(js/console.log :on-select-event %)
                   :on-select-slot #(rf/dispatch [:event-form/init-create (.-start %)])}]]))
