(ns app.utils.dates
  (:require ["moment" :as moment]))

(defn create-datetime [d t]
  (let [fallback-date (js/Date.)
        date-str (-> (or d fallback-date)
                     (moment)
                     (.format "YYYY-MM-DD")) #_(.format (moment d) "YYYY-MM-DD")
        time-str (-> (or t fallback-date)
                     (moment)
                     (.format "HH:mm:ss.SSSSZ")) #_(.format (moment t) "HH:mm:ss.SSSSZ")
        datetime-str (str date-str "T" time-str)]
    (-> datetime-str 
        (moment)
        (.toDate))))

(defn new-event [date]
  {:title ""
   :description ""
   :date (or date (js/Date.))
   :start nil
   :end nil
   :allDay false})
