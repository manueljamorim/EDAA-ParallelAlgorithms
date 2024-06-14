import pandas as pd
import matplotlib.pyplot as plt

# Updated dataset values based on provided image for Quick Sort and Odd Even MergeSort
data_values = {
    "Elements": ["10'000", "100'000", "1'000'000", "10'000'000", "100'000'000"],
    "Parallel Quick Sort Time": [3, 3, 27, 262, 8874],
    "Odd Even MergeSort Time": [11, 18, 93, 518, 1837],
}

sort_data = pd.DataFrame(data_values)

sort_data = sort_data.replace({None: pd.NA})

sort_data["Time Ratio"] = (
    sort_data["Parallel Quick Sort Time"] / sort_data["Odd Even MergeSort Time"]
)
sort_data["Time Ratio"] = (
    sort_data["Time Ratio"].replace(float("inf"), 0).replace(0, float("nan"))
)  # Replace zero ratios with NaN

colors = [
    "green" if quick < oem else "red"
    for quick, oem in zip(
        sort_data["Parallel Quick Sort Time"],
        sort_data["Odd Even MergeSort Time"],
    )
]


plt.figure(figsize=(12, 8))
x = range(len(sort_data["Elements"]))  # the label locations

rects = plt.bar(
    x,
    sort_data["Time Ratio"],
    width=0.35,
    color=colors,
    label="Time Ratio (Quick Sort / Odd Even MergeSort)",
)
plt.ylabel("Time Ratio (Quick Sort / Odd Even MergeSort)")
plt.title("Time Ratio of Paralelized Quick Sort to Odd Even MergeSort with Timings")
plt.xticks(x, sort_data["Elements"])
plt.xlabel("Number of Elements")
plt.legend()

# Replace infinite values with NaN
sort_data["Time Ratio"] = sort_data["Time Ratio"].replace(float("inf"), pd.NA)

# Calculate efficiency
sort_data["Efficiency"] = sort_data["Time Ratio"] / 8


ymax = sort_data["Time Ratio"].max() * 1.2
plt.ylim(
    0, ymax if not pd.isna(ymax) else 1
)  # Handle cases where all values might be NaN


def autolabel_with_timings(rects):
    for i, rect in enumerate(rects):
        height = rect.get_height()
        efficiency = sort_data["Efficiency"].iloc[i]
        label = (
            f"{height:.2f}\nEff: {efficiency:.2f}\nQuick: {sort_data['Parallel Quick Sort Time'].iloc[i]}ms\nOEM: {sort_data['Odd Even MergeSort Time'].iloc[i]}ms"
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
