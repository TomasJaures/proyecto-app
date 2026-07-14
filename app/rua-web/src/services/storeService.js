export const weekStorage = {
    storeActualWeek(actualWeek) {
        if (localStorage.getItem("actualWeek")) {
            localStorage.setItem("actualWeek", actualWeek);
            localStorage.setItem("currentWeek", actualWeek);
        } else {
            throw new Error("Ya se ha almacenado la semana actual");
        }
    },

    resetCurrentWeek(){
        const actualWeek = Number(localStorage.getItem("actualWeek"));
        localStorage.setItem("currentWeek", actualWeek)
    },

    increaseCurrentWeek() {
        const currentWeek = Number(localStorage.getItem("currentWeek"));
        localStorage.setItem("currentWeek", currentWeek + 1);
    },

    subtractCurrentWeek() {
        const currentWeek = Number(localStorage.getItem("currentWeek"));
        localStorage.setItem("currentWeek", currentWeek - 1);
    },

    getCurrentWeek(){
        return Number(localStorage.getItem("currentWeek"));
    }
};