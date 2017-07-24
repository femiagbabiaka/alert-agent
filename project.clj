(defproject alert-agent "0.1.0-SNAPSHOT"
  :description "Receives structured JSON metrics and emits alerts given certain criteria."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [cheshire "5.7.1"]
                 [automat "0.2.2"]]
  :main ^:skip-aot alert-agent.core
  :uberjar-name "alert-agent.jar"
  :profiles {:uberjar {:aot :all}})
