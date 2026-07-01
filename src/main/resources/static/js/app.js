document.addEventListener("DOMContentLoaded", () => {
    const filterButtons = document.querySelectorAll(".filter-button");
    const cards = document.querySelectorAll(".pattern-card");

    filterButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const selectedCategory = button.dataset.filter;
            filterButtons.forEach((item) => item.classList.remove("active"));
            button.classList.add("active");

            cards.forEach((card) => {
                const isMatch = selectedCategory === "ALL" || card.dataset.category === selectedCategory;
                card.classList.toggle("is-hidden", !isMatch);
            });
        });
    });

    if (window.mermaid) {
        mermaid.initialize({
            startOnLoad: true,
            theme: "base",
            themeVariables: {
                primaryColor: "#eef6f7",
                primaryTextColor: "#172026",
                primaryBorderColor: "#087f8c",
                lineColor: "#596874",
                secondaryColor: "#fff4e4",
                tertiaryColor: "#ffffff"
            }
        });
    }

    const data = window.patternExplorerData || { labels: [], popularity: [], complexity: [] };
    const chartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true,
                max: 100,
                grid: { color: "#edf2f5" }
            },
            x: {
                ticks: {
                    autoSkip: false,
                    maxRotation: 75,
                    minRotation: 60
                },
                grid: { display: false }
            }
        },
        plugins: {
            legend: { display: false }
        }
    };

    const popularityCanvas = document.getElementById("popularityChart");
    const complexityCanvas = document.getElementById("complexityChart");

    if (window.Chart && popularityCanvas) {
        new Chart(popularityCanvas, {
            type: "bar",
            data: {
                labels: data.labels,
                datasets: [{
                    label: "Popularity",
                    data: data.popularity,
                    backgroundColor: "#087f8c",
                    borderRadius: 4
                }]
            },
            options: chartOptions
        });
    }

    if (window.Chart && complexityCanvas) {
        new Chart(complexityCanvas, {
            type: "line",
            data: {
                labels: data.labels,
                datasets: [{
                    label: "Complexity",
                    data: data.complexity.map((score) => score * 20),
                    borderColor: "#d95d39",
                    backgroundColor: "rgba(217, 93, 57, 0.12)",
                    fill: true,
                    pointRadius: 4,
                    tension: 0.25
                }]
            },
            options: chartOptions
        });
    }
});
