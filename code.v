import 'string'

struct Animal() {
    let bird = 20
    let cat = 5
    echo bird / cat                                 ~ 4
    echo bird - cat + (cat * cat)                   ~ 40

    let t = true and (false or true) or false
    echo t                                          ~ true
}
