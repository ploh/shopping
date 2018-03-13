(defproject shopping-summit "0.1.0-SNAPSHOT"
  :description "Shopping cart as a service"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [bidi "2.1.3"
                  :exclusions [ring/ring-core]]
                 [ring "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [org.danielsz/system "0.4.1"
                  :exclusions [org.clojure/tools.reader
                               org.clojure/core.async]]
                 [org.clojure/java.jdbc "0.7.5"]
                 [com.h2database/h2 "1.4.196"]
                 [hikari-cp "2.2.0"]
                 [clj-http "3.8.0"]
                 [org.clojure/test.check "0.9.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.25"]
                 [ring/ring-mock "0.3.2"
                  :exclusions [cheshire]]]
  :pedantic? :abort
  :profiles {:uberjar {:main shopping-summit.main
                       :aot [shopping-summit.main]}})
