(ns advent.day1
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def numbers
  (->> "day1"
      io/resource
      slurp
      string/split-lines
      (map read-string)))

(defn first-part [nums]
  (reduce + nums))

(defn first-doubled-frequency [nums]
  (reduce
   (fn [seen val'] (if (seen val') (reduced val') (conj seen val')))
   #{0}
   (reductions + (cycle nums))))
