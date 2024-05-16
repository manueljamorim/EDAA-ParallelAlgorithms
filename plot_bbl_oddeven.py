import pandas as pd
import matplotlib.pyplot as plt

# Load the Excel file
file_path = "benchmark.xlsx"
data = pd.read_excel(file_path)

data.head()

bubble_sort_data = data.iloc[
    2:, [1, 4]
]  
bubble_sort_data.columns = ["Elements", "Bubble Sort Time"]
bubble_sort_data["Bubble Sort Time"] = (
    bubble_sort_data["Bubble Sort Time"]
    .astype(str)
    .str.replace("ms", "")
    .replace("nan", "0")
    .astype(int)
)

odd_even_merge_data = data.iloc[
    2:, [7, 10]
] 
odd_even_merge_data.columns = ["Elements", "Odd Even Sort Time"]
odd_even_merge_data["Odd Even Sort Time"] = (
    odd_even_merge_data["Odd Even Sort Time"]
    .astype(str)
    .str.replace("ms", "")
    .replace("nan", "0")
    .astype(int)
)


odd_even_merge_data["Elements"] = bubble_sort_data["Elements"]


bubble_odd_even_data = pd.merge(bubble_sort_data, odd_even_merge_data, on="Elements")
bubble_odd_even_data["Elements"] = (
    bubble_odd_even_data["Elements"].str.replace("'", "").astype(int)
)


bubble_odd_even_data["Time Ratio"] = (
    bubble_odd_even_data["Bubble Sort Time"]
    / bubble_odd_even_data["Odd Even Sort Time"]
)
bubble_odd_even_data["Time Ratio"] = (
    bubble_odd_even_data["Time Ratio"].replace(float("inf"), 0).replace(0, float("nan"))
)  # Replace zero ratios with NaN

colors = [
    "green" if bbl < oem else "red"
    for bbl, oem in zip(
        bubble_odd_even_data["Bubble Sort Time"],
        bubble_odd_even_data["Odd Even Sort Time"],
    )
]


plt.figure(figsize=(12, 8))
x = range(len(bubble_odd_even_data["Elements"]))  # the label locations

rects = plt.bar(
    x,
    bubble_odd_even_data["Time Ratio"],
    width=0.35,
    color=colors,
    label="Time Ratio (Bubble Sort / Odd Even Sort)",
)
plt.ylabel("Time Ratio (Bubble Sort / Odd Even Sort)")
plt.title("Time Ratio of Bubble Sort to Odd Even Sort with Timings")
plt.xticks(x, bubble_odd_even_data["Elements"])
plt.xlabel("Number of Elements")
plt.legend()

# Adjust y-axis scale by 10% to fit labels
ymax = bubble_odd_even_data["Time Ratio"].max() * 1.2
plt.ylim(
    0, ymax if not pd.isna(ymax) else 1
)  # Handle cases where all values might be NaN



def autolabel_with_timings(rects):
    for i, rect in enumerate(rects):
        height = rect.get_height()
        label = (
            f"{height:.2f}\nBBL: {bubble_odd_even_data['Bubble Sort Time'].iloc[i]}ms\nOES: {bubble_odd_even_data['Odd Even Sort Time'].iloc[i]}ms"
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
