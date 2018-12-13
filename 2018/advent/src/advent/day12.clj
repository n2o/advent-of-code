(ns advent.day12)

(def init
  "#........#.#.#...###..###..###.#..#....###.###.#.#...####..##..##.#####..##...#.#.....#...###.#.####")
(def alive #{"##..#" ".##.#" "..###" "###.#" "#.##." ".#.##" "###.." "#####" "##..." ".#..." "..#.." "...#." ".##.." "..#.#" ".####"})
(def dead #{".###." "#...#" "..##." "#..##" "....." "...##" ".#..#" "#...." "####." "....#" "#..#." "#.#.#" "##.#." ".#.#." "#.#.." "#.###" "##.##"})

(def test-input "#..#.#..##......###...###")
(def test-alive #{"...##" "..#.." ".#..." ".#.#." ".#.##" ".##.." ".####" "#.#.#" "#.###" "##.#." "##.##" "###.." "###.#" "####."})
(def test-dead #{})

(defn- make-rules [alive-set dead-set input]
  (let [str-input (apply str input)]
    (cond
      (contains? alive-set str-input) "#"
      :else ".")))

(def test-rules (partial make-rules test-alive test-dead))
(def init-rules (partial make-rules alive dead))

(defn step [input rules]
  (str
   ".."
   (apply str
          (map rules (partition 5 1 input)))
   ".."))

(defn twenty-steps [input padding rules]
  (let [padded-input (str padding input padding)]
    (loop [current padded-input
           steps 20]
      (if (zero? steps)
        current
        (recur (step current rules) (dec steps))))))

(defn first-part [input padding rules]
  (let [after-twenty-steps (twenty-steps input padding rules)]
    (reduce +
            (map #(- % (count padding))
                 (remove nil?
                         (map-indexed (fn [idx v] (when (= \# v) idx)) after-twenty-steps))))))

(comment
  (first-part test-input "..............." test-rules)
  (time
   (def res
     (first-part init
                 "........................................................................"
                 init-rules)))
  )
