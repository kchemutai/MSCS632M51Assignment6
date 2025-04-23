# stats.py
from collections import Counter

class StatisticsCalculator:
    def __init__(self, data):
        self.data = data

    def mean(self):
        return sum(self.data) / len(self.data)

    def median(self):
        sorted_data = sorted(self.data)
        n = len(sorted_data)
        mid = n // 2
        if n % 2 == 0:
            return (sorted_data[mid - 1] + sorted_data[mid]) / 2
        else:
            return sorted_data[mid]

    def mode(self):
        freq = Counter(self.data)
        max_count = max(freq.values())
        return [k for k, v in freq.items() if v == max_count]

if __name__ == "__main__":
    numbers = [1, 2, 2, 3, 4]
    stats = StatisticsCalculator(numbers)
    print(f"Mean: {stats.mean():.2f}")
    print(f"Median: {stats.median():.2f}")
    print(f"Mode(s): {stats.mode()}")
