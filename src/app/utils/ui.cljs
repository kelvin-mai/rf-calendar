(ns app.utils.ui
  (:require [reagent.core :as r]))

(defn v [e]
  (.. e
      -target
      -value))

(defn raw-component! [c p]
  (r/create-element c (clj->js p)))
