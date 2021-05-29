(ns app.components.calendar
  (:require [re-frame.core :as rf]
            ["@material-ui/core" :refer [Button
                                         Paper
                                         TextField]]
            ["@material-ui/pickers" :refer [MuiPickersUtilsProvider
                                            DatePicker
                                            KeyboardTimePicker]]
            ["react-big-calendar" :refer [Calendar
                                          momentLocalizer]]
            ["@date-io/moment" :as moment-utils]
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
                   :on-select-slot #(js/console.log :on-select-slot %)}]]))

(defn event-form []
  (let []
    [:form {:style {:width 460}
            :on-submit (fn [e]
                         (.preventDefault e)
                         (js/console.log e))}
     [:> MuiPickersUtilsProvider {:utils moment-utils}
      [:> DatePicker {:auto-ok true
                      :orientation "landscape"
                      :variant "static"
                      :open-to "date"
                      :value (js/Date.)
                      :on-change #(js/console.log :on-change/date %)}]
      [:> TextField {:label "Title"
                     :full-width true}]
      [:> TextField {:label "Description"
                     :full-width true}]
      [:> KeyboardTimePicker {:auto-ok true
                              :clearable true
                              :PopoverProps {:disableEnforceFocus true}
                              :variant "inline"
                              :label "Start"
                              :value (js/Date.)
                              :on-change #(js/console.log :on-change/start %)}]
      [:> KeyboardTimePicker {:auto-ok true
                              :clearable true
                              :PopoverProps {:disableEnforceFocus true}
                              :variant "inline"
                              :label "End"
                              :value (js/Date.)
                              :on-change #(js/console.log :on-change/end %)}]
      [:> Button {:type "submit"} "submit"]]]))
