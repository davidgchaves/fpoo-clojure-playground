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

;; A shift method -> Takes a particular point
;;                   and shifts it according to a x- and y-increment
(def shift
  (fn [this x-inc y-inc]
    (Point (+ (x this) x-inc)
           (+ (y this) y-inc))))

(shift (Point 10 10) 20 200)
