(ns alert-agent.utils.memory-store)

(def store nil)
(def current-timestamp nil)
(def has-alerted nil)



(defn increment-metric [service timestamp]
  (let [stamp (keyword (str timestamp))]
    (when (and (nil? (get store stamp)) (some? store))
      (def store nil)
      (def has-alerted nil))
    (def store {stamp (merge-with + (get store stamp) {(keyword service) 1})})
    (def current-timestamp stamp)))

(defn send-alert
  ([timestamp component value service server] (def has-alerted (merge has-alerted { (keyword service) true })) (println (str "ALERT timestamp: " timestamp ", component: " component ", value: " value ", server: " server ", service: " service)))
  ([timestamp component value service] (def has-alerted (merge has-alerted { (keyword service) true })) (println (str "ALERT timestamp: " timestamp ", component: " component ", value: " value ", service: " service))))
