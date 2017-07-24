(ns alert-agent.utils.parser
  (:require [cheshire.core :refer :all])
  (:import (java.io FileNotFoundException)))

(defn parse-metrics-json [filename]
  (try
    (parse-string (slurp (str filename)))
    (catch Exception e (println "Invalid file name."))))
