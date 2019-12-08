(ns aoc.day2
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))
(def input
  (mapv read-string
        (-> "day2"
            io/resource
            slurp
            (string/split #","))))

(defn one-step [code [opcode arg1 arg2 destination]]
  (let [val1 (nth code arg1)
        val2 (nth code arg2)]
    (case opcode
      1 (assoc code destination (+ val1 val2))
      2 (assoc code destination (* val1 val2)))))

(defn execute-code [code]
  (loop [code code
         pointer 0]
    (let [pointer' (+ pointer 4)]
      (if (and (< pointer' (count code))
               (not= 99 (nth code pointer)))
        (let [current (subvec code pointer pointer')]
          (recur (one-step code current) pointer'))
        code))))

(defn- prepare-input [code noun1 noun2]
  (assoc code 1 noun1 2 noun2))

(def solution1
  "4570637"
  (first (execute-code (prepare-input input 12 2))))

(def solution2
  "[54 85] => 100 * 54 + 85 = 5485"
  (remove nil?
          (for [verb (range 100)
                noun (range 100)]
            (when (= 19690720 (first (execute-code (prepare-input input verb noun))))
              [verb noun]))))