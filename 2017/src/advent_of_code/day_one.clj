(ns advent-of-code.day-one)

(defn- compare-cols [col1 col2]
  (reduce +
          (map #(if (= %1 %2) (read-string (str %1)) 0)
               col1 col2)))

(defn part-one [num]
  (let [col1 (str num)
        col2 (conj (vec (rest col1)) (first col1))]
    (compare-cols col1 col2)))

(defn part-two [num]
  (let [num (str num)
        half (/ (count num) 2)
        cols [(take half num) (drop half num)]
        col1 (flatten [(first cols) (second cols)])
        col2 (flatten [(second cols) (first cols)])]
    (compare-cols col1 col2)))
