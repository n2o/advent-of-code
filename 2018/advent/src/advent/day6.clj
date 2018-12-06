(ns advent.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def input
  (->> "day6"
       io/resource
       slurp
       string/split-lines
       (map (fn [s]
              (map read-string (string/split s #", "))))))

(defn- dimensions [input]
  (let [min-x (first (apply min-key first input))
        min-y (second (apply min-key second input))
        max-x (first (apply max-key first input))
        max-y (second (apply max-key second input))]
    [(inc (- max-x min-x)) (inc (- max-y min-y))]))

(defn- manhattan-dist [u v]
  (reduce +
          (map (fn [[a b]] (Math/abs (- a b)))
               (map vector u v))))

(defn- manhattan-dists-for-coord [x y]
  (map-indexed vector
               (map #(manhattan-dist % [x y]) input)))

(defn- find-fitting-keyword
  "Returns keyword representation of position in input, else :. as in the
  example."
  [[x y]]
  (let [dists (manhattan-dists-for-coord x y)
        [manhattan-idx manhattan-min] (first (sort-by second dists))
        smallest-vecs (remove #(< manhattan-min (second %)) dists)]
    (if (< 1 (count smallest-vecs))
      :.
      (keyword (str manhattan-idx)))))

(defn- infinite-idxs [grid height]
  (let [parts (partition (inc height) grid)
        first-row (first parts)
        last-row (last parts)
        left-col (map first parts)
        right-col (map last parts)
        on-borders (distinct (flatten (apply conj first-row last-row left-col right-col)))]
    on-borders))

(defn first-part []
  (let [[width height] (dimensions input)
        grid (pmap find-fitting-keyword (for [x (range (inc width))
                                               y (range (inc height))]
                                           [x y]))
        area (frequencies grid)
        infinite (infinite-idxs grid height)
        without-infinite-borders (apply dissoc area infinite)
        biggest-area (second (apply max-key val without-infinite-borders))]
    biggest-area))


;; -----------------------------------------------------------------------------

(defn second-part []
  (let [[width height] (dimensions input)
        grid-dimensions (for [x (range (+ 100 width))
                              y (range (+ 100 height))]
                          [x y])
        distance-vecs (pmap (fn [[x y]] (manhattan-dists-for-coord x y)) grid-dimensions)
        distances (pmap #(reduce + (map second %)) distance-vecs)]

    (count (filter #(< % 10000) distances))))

(comment
  (first-part)  ;; => 3420
  (second-part)  ;; => 46667
  )
