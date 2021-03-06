(ns advent.day18
  (:require [clojure.string :as string]))

(def test-puzzle
  [".#.#...|#."
   ".....#|##|"
   ".|..|...#."
   "..|#.....#"
   "#.#|||#|#|"
   "...#.||..."
   ".|....|..."
   "||...#|.#|"
   "|.||||..|."
   "...#.|..|."])

(def puzzle
  ["#||.|...#.......#|#|....#.|#.#.|...#.|#........##."
   "...#...##.#...##|.....|..|....|......#||..#.|.##.#"
   "..||..####|||.||#|....##|#.##|.#...##.|.|.||.#.##|"
   ".||#..||.||..|||...|.....##..#..........||.#..#..."
   "|...|...|.....#......||#|..|.....##.||.#..##|.#||#"
   "|...#.|.||#|#..|..|..#|#|.##.....|...#|.#....#..#."
   "|...#...#|#.|#.#||....#..||.|..|.||.|.|.....#..|#|"
   "..|..##..#..|###.||...|||#.#...#.#....##...|..|..|"
   "..##...#.......#|#|#...#..##.#........|.|#......|."
   "|..|#..|##|...#....#.##|...#.....|...........|#..|"
   "|....|.|#..##|.|##||.#.....|.#..#|.#.#|#|.......#|"
   "||..#.##..##...#|#.#...#|.##..###.....#..#.|..|##|"
   "#|#|..|.#.|.#.|.|...||#.|..#....#..|...||..|..#|.#"
   ".###..|....|.#||#..##.#|..|||..##|.||....|.#.|...."
   "|....#..#####......|||||##..|...#........#..|...##"
   "..##........|...||......##.#.#...|..|#...#|....|.."
   "||...#.#|..#||.....|#.#.|.....|.|..#.#.|....#.#..|"
   "......#..##.|.#|..|||#.........|#.|#|.|...|.|#..#."
   "||...#..##|..|#.#|.....#.|...|.|...|.|.|..#.#..#.|"
   "....#...#..###||.||.##....#....||..|...||.|..|...#"
   ".#..#.#|....|.#.|#|.....#|.......||..##...#..#|.|."
   "......#||##..#...#..|..#.|....||...#...|.#...#.#.|"
   "....||.....|.|#..||.#||....#..#.|.#...||.|.|....#."
   "#.|.#.#..|.....|||.......#.#.#.|.#......#....||#.|"
   "|....##......|.||#.|...#.#...#|...|.||..|.#..|...."
   "||##..#......#|.#|.||||#.#...#.#.###.|#..#####...."
   "#.|..|#|#....#.|..||#|###|..|.|...|.#..|.|.#..##||"
   "..#..#..#...|..#.|....|||..#||.|.....|..#......||."
   "||....||..|#||....|..#.##..|||#|.#|.#.||.||..||..."
   ".|.|.|##..|.##..|....#.#.#||....#...#|.#....##.#.|"
   "|.#....|.#...#..#...|.#.|#...|||..|#.#...|...#...|"
   "..|##...#..#.....|.|.#....#....||.#.|.#......|||##"
   "...||#...#|#........#..#.|.#...|||.......###..#..."
   "#.|.|#.#..#...#.....#..#....|.|..#...#.###.....#|#"
   "#...|.#..|.|||..||...##..|.....|##...||....###.##."
   "##|.#..##..|.|.#.#.|#..##...|..|.#.||#..|...#|..##"
   ".#...#....##....||.#.|.|.|..|.|.###......#.###.|.#"
   "|.|.#..|#.#|..#.#...|.#|||#.#.....#.|#|||..#..|#.."
   "...|#...||#.#.|....|......|..#|.|.......#.##..##.."
   "..#....|.#.|....#|##...#||##......|#.|..|.|..#.|.."
   "||.|.#|.|#|#.......|.|#...|.#...|#..|....###..#.##"
   "..|.#|...|.#.#.#.#.#|..#...#..#|...#...#.......#.."
   "##|.....||#...#...|...|#...#.#......||..........|#"
   "..##.##....#.|.##..|#....|#...#|....#.##|.|#.||..."
   ".|....#...|...#..|##.###|#.#..|...||||.#.#.|.#...."
   "|..#|....|...#.....|..#|..||.|......#..||...#|.|.|"
   "|...#.#||..#..||.....|.....#|||...|...#|..|#.#|#.#"
   ".|.........#..|###......||.|#|..||#.|..|.|.....|.#"
   "||#..|..|.||#|.#|##|...#.##..#||.|....##....|.#||."
   ".#....|#.#....|.|.|#..#......|||...#....|........."])

(defn current-symbol [puzzle [x y]]
  (when-not (or (neg? x) (neg? y)
                (> x (dec (count (first puzzle))))
                (> y (dec (count puzzle))))
    (nth (nth puzzle y) x)))

(defn neighborhood [x y]
  (for [xs (range (dec x) (+ 2 x))
        ys (range (dec y) (+ 2 y))]
    [xs ys]))

(defn valid-neighborhood [x y]
  (let [positions (neighborhood x y)]
    (remove (fn [[x' y']] (and (= x x') (= y y'))) positions)))

(defn classify-neighborhood [puzzle [x y]]
  (frequencies (map (partial current-symbol puzzle) (valid-neighborhood x y))))

(defn rules [puzzle [x y]]
  (let [current (current-symbol puzzle [x y])
        classified (classify-neighborhood puzzle [x y])
        trees (get classified \| 0)
        lumberyards (get classified \# 0)]
    (cond
      (and (= \. current) (<= 3 trees)) \|  ;; rule 1
      (and (= \| current) (<= 3 lumberyards)) \#  ;; rule 2
      (and (= \# current) (<= 1 lumberyards) (<= 1 trees)) \#  ;; rule 3, part 1
      (= \# current) \.  ;; rule 3, part 2
      :else current)))

(defn field-positions [puzzle]
  (for [xs (range (count (first puzzle)))
        ys (range (count puzzle))]
    [xs ys]))

(defn execute-steps [puzzle steps]
  (let [positions (field-positions puzzle)]
    (loop [current-puzzle puzzle
           steps steps]
      (if (zero? steps)
        current-puzzle
        (recur
         (apply map str
                (partition (count current-puzzle)
                           (map (partial rules current-puzzle) positions)))
         (dec steps))))))

(defn build-solution [puzzle steps]
  (let [after-ten-steps (execute-steps puzzle steps)
        classified (frequencies (string/join after-ten-steps))]
    (* (get classified \|) (get classified \#))))


;; -----------------------------------------------------------------------------

(defn first-part [puzzle]
  (time
   (build-solution puzzle 10)))

(defn second-part [puzzle]
  (time
   (build-solution puzzle 1000000000)))

(comment
  (first-part puzzle)
  (second-part puzzle)

  (= \| (rules puzzle [1 8]))
  (= \# (rules puzzle [7 0]))
  (= \# (rules puzzle [8 0]))
  (= \. (rules puzzle [1 0]))
  )
