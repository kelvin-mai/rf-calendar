(ns app.components.ui
  (:require [re-frame.core :as rf]
            ["@material-ui/core/styles" :refer [createMuiTheme]]
            ["@material-ui/core" :refer [CssBaseline
                                         ThemeProvider
                                         AppBar
                                         Drawer
                                         IconButton
                                         Switch
                                         Typography
                                         Toolbar
                                         Tooltip]]
            ["@material-ui/icons/AddBox" :default AddIcon]))

(defn create-theme [theme]
  (createMuiTheme
   (clj->js {:palette {:type theme
                       :primary {:main "#3174ad"}}})))

(defn container
  [children]
  (let [theme @(rf/subscribe [:ui/theme])
        text @(rf/subscribe [:text])]
    [:> ThemeProvider {:theme (create-theme theme)}
     [:> CssBaseline]
     [:> AppBar {:position "static"}
      [:> Toolbar
       [:> IconButton {:on-click #(rf/dispatch [:event-form/init-create])}
        [:> AddIcon]]
       [:> Typography {:component "h1"
                       :variant "h6"
                       :style {:flex-grow 1
                               :padding-left "1rem"}
                       :no-wrap true}
        text]
       [:> Tooltip {:title "Toggle dark theme"}
        [:> Switch {:checked (= theme "dark")
                    :on-change #(rf/dispatch [:ui/toggle-theme])}]]]]
     [:main {:style {:padding "1rem"}}
      children]]))

(defn sidebar
  [children]
  (let [open? @(rf/subscribe [:ui/sidebar-open?])]
    [:> Drawer {:anchor "left"
                :open open?
                :on-close #(rf/dispatch [:ui/toggle-sidebar])}
     children]))
