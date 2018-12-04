(ns advent.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as cset]))

(def input
  (->> "day3"
       io/resource
       slurp
       string/split-lines))

(defn parse-line [line]
  (let [[case x y width height] (map read-string (re-seq #"[0-9]+" line))]
    {:case case, :x x, :y y
     :width width, :height height}))

(defn coords-of-field [{:keys [x y width height]}]
  (for [xs (range x (+ x width))
        ys (range y (+ y height))]
    [xs ys]))

(defn first-part [data]
  (let [vec-of-positions (map coords-of-field (map parse-line data))
        merged-positions (apply concat vec-of-positions)
        duplicate-vecs (count (filter #(< 1 (second %))
                                      (frequencies merged-positions)))]
    duplicate-vecs))


;; -----------------------------------------------------------------------------

(defn- case-with-coords [parsed-line]
  {:case (:case parsed-line) :coords (coords-of-field parsed-line)})

(defn- all-combinations [test-data]
  (let [parsed-lines (map parse-line test-data)
        with-cases (map case-with-coords parsed-lines)]
    with-cases))

(defn- empty-intersection? [[to-be-checked & other-vecs]]
  (let [matching-vec (set (:coords to-be-checked))
        other-vecs (set (apply concat (apply map :coords other-vecs)))]
    (empty? (cset/intersection matching-vec other-vecs))))

(defn- build-combinations [col]
  (for [x (all-combinations col)]
    [x (remove #(= (:case x) (:case %)) (all-combinations col))]))

(defn part-two [data]
  (let [combs (build-combinations data)]
    (loop [case (first combs)
           remaining (rest combs)]
      (if (empty-intersection? case)
        (:case (first case))
        (recur (first remaining) (rest remaining))))))

(comment
  (part-two input)
  )
