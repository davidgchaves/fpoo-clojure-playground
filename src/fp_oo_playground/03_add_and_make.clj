(ns fp-oo-playground.03-add-and-make)

;; Constructor for a Point class using a map internally
(def Point
  (fn [x y]
    {:x x
     :y y
     :__class_symbol__ 'Point
     }))

;; 'Instance variables' are not encapsulated, so we can access them directly
(:x (Point 1 2))
(:y (Point 1 2))
(:__class_symbol__ (Point 1 2))

;; Wrapping our 'instance variables' in accessors (getter) methods
(def x :x)
(def y :y)
(def class-of :__class_symbol__)

;; Now we can access our 'instance variables' through the getters
(x (Point 1 2))
(y (Point 1 2))
(class-of (Point 1 2))

;; Shift -> Takes a particular point
;;          Shifts it according to a x- and y-increment
(def shift
  (fn [this x-inc y-inc]
    (Point (+ (x this) x-inc)
           (+ (y this) y-inc))))

(shift (Point 10 10) 20 200)

;; Add -> Implement an add function that adds two point producing a third
(def add
  (fn [this that]
    (Point (+ (x this) (x that))
           (+ (y this) (y that)))))

(add (Point 10 50) (Point 20 100))

;; Add -> Implement an add function that adds two point producing a third
;;        Use the 'shift' function
(def add-shift
  (fn [this that]
    (shift this (x that) (y that))))

(add-shift (Point 10 50) (Point 20 100))
