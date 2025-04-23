(* stats.ml *)
let mean lst =
  let sum = List.fold_left (+) 0 lst in
  float_of_int sum /. float_of_int (List.length lst)

let median lst =
  let sorted = List.sort compare lst in
  let len = List.length sorted in
  if len mod 2 = 0 then
    let mid1 = List.nth sorted (len/2 - 1) in
    let mid2 = List.nth sorted (len/2) in
    (float_of_int (mid1 + mid2)) /. 2.0
  else
    float_of_int (List.nth sorted (len/2))

let mode lst =
  let freq_table = List.fold_left (fun acc x ->
    if List.mem_assoc x acc then
      (x, (List.assoc x acc + 1)) :: List.remove_assoc x acc
    else
      (x, 1) :: acc
  ) [] lst in
  let max_freq = List.fold_left (fun acc (_, count) -> max acc count) 0 freq_table in
  List.filter (fun (_, count) -> count = max_freq) freq_table
  |> List.map fst

let () =
  let nums = [1; 2; 2; 3; 4] in
  Printf.printf "Mean: %.2f\n" (mean nums);
  Printf.printf "Median: %.2f\n" (median nums);
  Printf.printf "Mode(s): ";
  List.iter (Printf.printf "%d ") (mode nums);
  Printf.printf "\n"
