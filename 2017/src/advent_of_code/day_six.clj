(ns advent-of-code.day-six)

(def state (atom 0))

(defn- biggest-element [vec]
  (apply max-key second (reverse (map-indexed vector vec))))

(defn- step [v]
  (let [[idx sum] (biggest-element v)
        myvec (assoc v idx 0)]
    (loop [vecmod myvec
           n sum
           i (mod (inc idx) (count vecmod))]
      (if (zero? n)
        vecmod
        (recur (assoc vecmod i (inc (nth vecmod i)))
               (dec n)
               (mod (inc i) (count vecmod)))))))

(defn run [v]
  (loop [myvec (step v)
         iterations 1
         seen [v myvec]]
    (if-not (apply distinct? seen)
      [myvec iterations]
      (let [v2 (step myvec)]
        (recur v2 (inc iterations) (conj seen v2))))))
;; (run [2 4 1 2])
