import axios from "axios";

const api = axios.create({
    baseURL:
        import.meta.env.VITE_API_URL ||
        "http://localhost:8080/api",
});

api.interceptors.request.use(
    (config) => {

        const token = localStorage.getItem("token");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);


// Handle expired / invalid JWT automatically
api.interceptors.response.use(

    (response) => response,

    (error) => {

        if (
            error.response?.status === 401
        ) {

            // Don't redirect if login itself failed
            const url = error.config?.url || "";

            if (!url.includes("/auth/login") &&
                !url.includes("/auth/register")) {

                localStorage.removeItem("token");
                localStorage.removeItem("role");
                localStorage.removeItem("email");

                window.location.href = "/login";
            }
        }

        return Promise.reject(error);
    }
);

export default api;