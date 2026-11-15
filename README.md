# Лаборатори 10 — Өгөгдлийн урсгалын тест

С. Энхбаяр / B232270153 / F.CSA313

## A. gcd-ийн DU хосууд

Мөрийн дугаар нь `DataFlowTargets.java` файлтай таарна. `13`-т `x`, `y` параметрүүд утгатай орж ирнэ. `14`-ийн `int tmp;` нь утга оноогоогүй зарлалт тул def гэж тооцоогүй.

`T`, `F` нь `15: y != 0` нөхцөлийн хоёр салаа. Хүснэгтэд 11 ялгаатай def–use хосыг p-use-ийн салаагаар нь салгаж, 13 мөрөөр харууллаа. Замын турш тухайн хувьсагчид дахин утга оноохгүй.

| Хувьсагч | Def мөр | Use мөр | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---:|---:|---|---|---|---|
| x | 13 | 16 | c-use | 13 → 15T → 16 | G02, G03 | Тийм |
| x | 13 | 20 | c-use | 13 → 15F → 20 | G01 | Тийм |
| x | 17 | 16 | c-use | 17 → 18 → 15T → 16 | G02 | Тийм |
| x | 17 | 20 | c-use | 17 → 18 → 15F → 20 | G02, G03 | Тийм |
| y | 13 | 15 (T) | p-use | 13 → 15T → 16 | G02, G03 | Тийм |
| y | 13 | 15 (F) | p-use | 13 → 15F → 20 | G01 | Тийм |
| y | 13 | 16 | c-use | 13 → 15T → 16 | G02, G03 | Тийм |
| y | 13 | 17 | c-use | 13 → 15T → 16 → 17 | G02, G03 | Тийм |
| y | 18 | 15 (T) | p-use | 18 → 15T → 16 | G02 | Тийм |
| y | 18 | 15 (F) | p-use | 18 → 15F → 20 | G02, G03 | Тийм |
| y | 18 | 16 | c-use | 18 → 15T → 16 | G02 | Тийм |
| y | 18 | 17 | c-use | 18 → 15T → 16 → 17 | G02 | Тийм |
| tmp | 16 | 18 | c-use | 16 → 17 → 18 | G02, G03 | Тийм |

### Тестүүд

| ID | Метод | Оролт | Хүлээгдэх хариу |
|---|---|---|---:|
| G01 | `gcd_zeroSecond_coversEntryToExitDu` | `(7, 0)` | 7 |
| G02 | `gcd_twoIterations_coversLoopCarriedDu` | `(12, 8)` | 4 |
| G03 | `gcd_zeroFirst_returnsSecond` | `(0, 5)` | 5 |

G01 давталтгүй замыг, G02 хоёр удаа давтаж үлдсэн DU хосуудыг шалгасан. G03 эхний оролт тэг байх тохиолдлыг шалгана.

### Ажиллуулах

```bash
mvn clean verify
```

Compiler тохиргоог зассан. Эх код өөрчлөгдөөгүй.

А шатанд WSL дээр 3 тест тэнцсэн. Нийт классын Branch: 2/16, Line: 5/24.

Үр дүн: [Maven гаралт](results/mvn-verify-A.txt), [JaCoCo CSV](results/jacoco-A.csv). DU хамралтыг хүснэгтээр шалгасан.

## B. bonus — all-defs

### Def/use жагсаалт

```text
31: def attendance, threshold
32: p-use attendance, threshold (null бол threshold-ийг шалгахгүй)
33: throw; def/use байхгүй
35: def present
36: def streak
37: def best
38: p-use attendance (дараагийн элемент байгаа эсэх)
38: def a (дараагийн элементийг оноох)
39: p-use a
40: c-use present; def present
41: c-use streak; def streak
42: p-use streak, best
43: c-use streak; def best
46: def streak; өмнөх streak-ийн утгыг дарна
49: def score
50: p-use present, threshold
51: def score
53: p-use best
54: c-use score; def score
56: c-use score
```

### Хоёр тест

| ID | Метод | attendance | threshold | Хариу |
|---|---|---|---:|---:|
| B01 | `bonus_belowThresholdWithLongStreak_returns3` | `[1,1,1,1,1,0]` | 7 | 3 |
| B02 | `bonus_afterAbsenceAtThreshold_returns5` | `[0,1]` | 1 | 5 |

B01 нь `score: 49 → 54 → 56`, B02 нь `score: 51 → 56` замыг шалгасан. Нэг тестээр хангахгүй: 51-р мөр ажиллавал 49-ийн утгыг хэрэглэхээс өмнө дарна. B02-т тасалсны дараа ирсэн тул 46-р мөрийн `streak = 0` утга 41-д хэрэглэгдэнэ.

WSL дээр нийт 5 тест тэнцсэн: gcd 3, bonus 2. Branch: 13/16 (81.25%), Line: 22/24. All-defs хангагдсан ч null, сөрөг threshold, `streak > best`-ийн F салаа хамрагдаагүй.

Үр дүн: [Maven гаралт](results/mvn-verify-alldefs.txt), [JaCoCo CSV](results/jacoco-alldefs.csv).

### All-defs-д ашигласан DU замууд

Def бүрээс нэг use-д хүрсэн замыг харуулав. Бүх DU хосын хүснэгтийг дараагийн шатанд гүйцээнэ. `32a/32b` нь 32-р мөрийн null/threshold шалгалт, `38p/38d` нь 38-р мөрийн нөхцөл/оноолт; `T/F` нь салаа.

| Хувьсагч | Def мөр | Use мөр | Төрөл | Def-clear зам | Нотлох тест | Хамрагдсан |
|---|---:|---|---|---|---|---|
| attendance | 31 | 32 (null) | p-use | 31 → 32a | B01 | Тийм |
| threshold | 31 | 32 (threshold) | p-use | 31 → 32aF → 32b | B01 | Тийм |
| present | 35 | 40 | c-use | 35 → 36 → 37 → 38pT → 38d → 39T → 40 | B01 | Тийм |
| present | 40 | 50 | p-use | 40 → 41 → 42T → 43 → 38pF → 49 → 50 | B02 | Тийм |
| streak | 36 | 41 | c-use | 36 → 37 → 38pT → 38d → 39T → 40 → 41 | B01 | Тийм |
| streak | 41 | 42 | p-use | 41 → 42 | B01 | Тийм |
| streak | 46 | 41 | c-use | 46 → 38pT → 38d → 39T → 40 → 41 | B02 | Тийм |
| best | 37 | 42 | p-use | 37 → 38pT → 38d → 39T → 40 → 41 → 42 | B01 | Тийм |
| best | 43 | 53 | p-use | 43 → 38pF → 49 → 50T → 51 → 53 | B02 | Тийм |
| a | 38 | 39 | p-use | 38d → 39 | B01 | Тийм |
| score | 49 | 54 | c-use | 49 → 50F → 53T → 54 | B01 | Тийм |
| score | 51 | 56 | c-use | 51 → 53F → 56 | B02 | Тийм |
| score | 54 | 56 | c-use | 54 → 56 | B01 | Тийм |

46-р мөр нь `streak`-ийн 36, 41, эсвэл өмнөх 46-р мөрийн def-ийг дарна.
