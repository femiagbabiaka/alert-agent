(ns alert-agent.core
  (require [clojure.tools.cli :refer [parse-opts]])
  (require [clojure.string :as string])
  (:require [alert-agent.utils.parser :as parser])
  (:require [alert-agent.events :as events])
  (:gen-class))

(defn -main [& args]
  (if (some? args)
    (let [metrics-map (parser/parse-metrics-json (string/join args))
          map-length (count metrics-map)]
      (loop [index 0]
        (when (< index map-length)
          (events/trigger-event (nth metrics-map index))
          (events/check-store)
          (recur (inc index)))))
   (println "An argument must be passed.")))
