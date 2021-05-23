(ns app.views
  (:require [re-frame.core :as rf]))

(defn app []
  (let [text @(rf/subscribe [:text])]
    [:<> 
     [:h2 text]]))
