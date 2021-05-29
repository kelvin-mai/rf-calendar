(ns app.components.event-form
  (:require [re-frame.core :as rf]
            [app.utils.ui :refer [v
                                  raw-component!]]
            ["@material-ui/core" :refer [Button
                                         Checkbox
                                         Divider
                                         FormControlLabel
                                         TextField]]
            ["@material-ui/pickers" :refer [MuiPickersUtilsProvider
                                            DatePicker
                                            KeyboardTimePicker]]
            ["@date-io/moment" :as moment-utils]))

(defn event-form-text-field
  [{:keys [label field value multiline? required?]}]
  [:> TextField {:style {:margin-bottom "0.5rem"}
                 :label label
                 :default-value value
                 :full-width true
                 :multiline multiline?
                 :required required?
                 :rows 4
                 :on-change #(rf/dispatch [:event-form/update-field field (v %)])}])

(defn event-form-time-field
  [{:keys [label field value full-width? disabled?]}]
  [:> KeyboardTimePicker {:auto-ok true
                          :PopoverProps {:disableEnforceFocus true}
                          :variant "inline"
                          :label label
                          :value value
                          :full-width full-width?
                          :disabled disabled?
                          :on-change #(rf/dispatch [:event-form/update-field field %])}])

(defn event-form [values]
  (let [{:keys [title
                description
                date
                start
                end
                allDay]} values]
    [:form {:style {:width 460}
            :on-submit (fn [e]
                         (.preventDefault e)
                         (rf/dispatch [:event-form/submit-create]))}
     [:> MuiPickersUtilsProvider {:utils moment-utils}
      [:> DatePicker {:auto-ok true
                      :orientation "landscape"
                      :variant "static"
                      :open-to "date"
                      :value date
                      :on-change #(rf/dispatch [:event-form/update-field :date
                                                %])}]
      [:> Divider]
      [:div {:style {:padding "1rem"}}
       [event-form-text-field {:label "Title"
                               :field :title
                               :value title
                               :required? true}]
       [event-form-text-field {:label "Description"
                               :field :description
                               :value description
                               :multiline? true}]
       [event-form-time-field {:label "Start Time"
                               :field :start
                               :value start
                               :full-width? true}]
       [:div {:style {:display "flex"
                      :justify-content "space-between"}}
        [event-form-time-field {:label "End Time"
                                :field :end
                                :value end
                                :disabled? allDay}]
        [:> FormControlLabel {:label "All day?"
                              :label-placement "start"
                              :control (raw-component!
                                        Checkbox
                                        {:checked allDay
                                         :color "primary"
                                         :value allDay
                                         :onChange #(rf/dispatch [:event-form/update-field
                                                                  :allDay
                                                                  (not allDay)])})}]]
       [:div {:style {:margin-top "1rem"
                      :display "flex"
                      :justify-content "space-between"}}
        [:> Button {:variant "contained"
                    :color "default"
                    :on-click #(rf/dispatch [:ui/toggle-sidebar])}
         "Cancel"]
        [:> Button {:type "submit"
                    :variant "contained"
                    :color "primary"}
         "Submit"]]]]]))
