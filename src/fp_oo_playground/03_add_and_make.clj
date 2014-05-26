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

;; Make -> A constructor-like functions
(def make
  (fn [type & args]
    (apply type args)))

(make Point 4 6)

;; A Triangle
(def Triangle
  (fn [p1 p2 p3]
    {:point1 p1,
     :point2 p2,
     :point3 p3,
     :__class_symbol__ 'Triangle}))

;; Make works with Triangles too
(make Triangle
      (make Point 1 2)
      (make Point 1 3)
      (make Point 3 1))

;; Three Triangles
(def right-triangle (Triangle (Point 0 0)
                              (Point 0 1)
                              (Point 1 0)))

(def equal-right-triangle (Triangle (Point 0 0)
                                    (Point 0 1)
                                    (Point 1 0)))

(def different-triangle (Triangle (Point 0 0)
                                  (Point 0 10)
                                  (Point 10 0)))

;; equal-triangles?
;; NOTE: In clojure there's no distiction between "pointer equality"
;;       and "content equality".
(def equal-triangles? =)

(equal-triangles? right-triangle right-triangle)
(equal-triangles? right-triangle equal-right-triangle)
(equal-triangles? right-triangle different-triangle)
(equal-triangles? right-triangle equal-right-triangle right-triangle)
(equal-triangles? right-triangle equal-right-triangle different-triangle)
