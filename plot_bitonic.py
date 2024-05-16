import pandas as pd
import matplotlib.pyplot as plt

data_values = {
    "Elements": ["10'000", "100'000", "1'000'000", "10'000'000", "100'000'000"],
    "Parallel Quick Sort Time": [3, 3, 27, 262, 8874],
    "Bitonic Sort Time": [34, 310, 951, 2160, 11595]
}

sort_data = pd.DataFrame(data_values)

sort_data = sort_data.replace({None: pd.NA})

sort_data["Time Ratio"] = (
    sort_data["Parallel Quick Sort Time"]
    / sort_data["Bitonic Sort Time"]
)
sort_data["Time Ratio"] = (
    sort_data["Time Ratio"].replace(float("inf"), 0).replace(0, float("nan"))
)  # Replace zero ratios with NaN

colors = [
    "green" if quick < bitonic else "red"
    for quick, bitonic in zip(
        sort_data["Parallel Quick Sort Time"],
        sort_data["Bitonic Sort Time"],
    )
]

plt.figure(figsize=(12, 8))
x = range(len(sort_data["Elements"]))  # the label locations

rects = plt.bar(
    x,
    sort_data["Time Ratio"],
    width=0.35,
    color=colors,
    label="Time Ratio (Parallel Quick Sort / Bitonic Sort)",
)
plt.ylabel("Time Ratio (Parallel Quick Sort / Bitonic Sort)")
plt.title("Time Ratio of Parallel Quick Sort to Bitonic Sort with Timings")
plt.xticks(x, sort_data["Elements"])
plt.xlabel("Number of Elements")
plt.legend()

# Adjust y-axis scale by 10% to fit labels
ymax = sort_data["Time Ratio"].max() * 1.2
plt.ylim(
    0, ymax if not pd.isna(ymax) else 1
)  # Handle cases where all values might be NaN

def autolabel_with_timings(rects):
    for i, rect in enumerate(rects):
        height = rect.get_height()
        label = (
            f"{height:.2f}\nQuick: {sort_data['Parallel Quick Sort Time'].iloc[i]}ms\nBitonic: {sort_data['Bitonic Sort Time'].iloc[i]}ms"
            if not pd.isna(height)
            else ""
        )
        plt.annotate(
            label,
            xy=(
                rect.get_x() + rect.get_width() / 2,
                height if not pd.isna(height) else 0,
            ),
            xytext=(0, 3),  # 3 points vertical offset
            textcoords="offset points",
            ha="center",
            va="bottom",
        )

autolabel_with_timings(rects)
plt.show()
