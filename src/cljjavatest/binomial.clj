(ns cljjavatest.binomial
  (:require [cljjavatest.sub :as sub])
  (:gen-class)
 )

(defn -binomial
  "A Java-callable wrapper around the 'binomial' function."
  [n k]
  (sub/binomial n k))

(defn impl-foo
  [this n k & {:keys [alpha gama beta]}]
  (println (str "test string" alpha gama beta))
  (str (sub/binomial n k)))

(gen-class
  :name cljjavatest.foo
  :prefix "impl-"
  :methods [[foowrapper [Integer int java.util.Map] String]])
(defn impl-foowrapper
  [this n k m]
  (if (nil? m)
    (impl-foo this n k)
    (impl-foo this n k :alpha (get m "alpha") :gama (get m "gama") :beta (get m "beta"))
    )
  )

(defn -main []
  (println (str "(binomial 5 3): " (-binomial 5 3)))
  (println (str "(binomial 10042 111): " (-binomial 10042 111)))
  ;(println (impl-foo 5 3))
  )
