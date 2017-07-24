(ns alert-agent.events
  (require [alert-agent.utils.memory-store :as store]))

(defn check-store-for [service upper-limit]
  (if-not (nil? (get-in store/store [store/current-timestamp service]))
    (when (and (nil? (get store/has-alerted service)) (>= (get-in store/store [store/current-timestamp service]) 1))
      (store/send-alert (name store/current-timestamp) "Load5mAvg" "4.0" (name service)))))

(defn check-store []
  (when-not (nil? store/store)
    (check-store-for :FacebookRealtime 1)
    (check-store-for :InboxService 2)
    (check-store-for :ReportPDFGenerator 1)))

(defmulti trigger-event
          (fn [metric]
            (str (metric "component"))))

(defmethod trigger-event :default
  [_]
  (println "Unrecognized metric."))

(defmethod trigger-event "DiskUsedPercentage"
  [metric]
  (if (and (= (metric "active") true) (> (metric "value") 0.9))
    (println (str "ALERT timestamp: " (metric "timestamp") ", "
                  "component: " (metric "component") ", "
                  "value: " (metric "value") ", "
                  "server: " (metric "server") ", "
                  "service: " (metric "service")))))

(defmethod trigger-event "Load5mAvg"
  [metric]
  (if (and (= (metric "active") true) (>= (metric "value") 4))
    (store/increment-metric (metric "service") (metric "timestamp"))))