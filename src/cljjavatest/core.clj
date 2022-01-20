(ns cljjavatest.core
  (:require [cljjavatest.sub :as sub])
  (:gen-class)
  )

(defn -main
  [& args]
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (sub/foo)
  (println x "Hello, World!"))
