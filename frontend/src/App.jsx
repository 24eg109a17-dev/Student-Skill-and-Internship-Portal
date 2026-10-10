import React, { useEffect, useState } from "react";
import {
    Routes,
    Route,
    Navigate,
    Link,
    useNavigate,
    useParams
} from "react-router-dom";

import api from "./api";


/* =========================
   AUTH
========================= */

const saveAuth = (x) => {
    localStorage.setItem("token", x.token);
    localStorage.setItem("role", x.role);
    localStorage.setItem("email", x.email);
};


/* =========================
   LAYOUT
========================= */

function Layout({ children }) {

    const nav = useNavigate();
    const role = localStorage.getItem("role");

    return (
        <>
            <header>

                <Link
                    to="/dashboard"
                    className="brand"
                >
                    SkillBridge
                </Link>

                <nav>

                    {role === "ADMIN" ? (

                        <Link to="/admin">
                            Admin
                        </Link>

                    ) : (

                        <>
                            <Link to="/internships">
                                Internships
                            </Link>

                            <Link to="/applications">
                                Applications
                            </Link>

                            <Link to="/profile">
                                Profile
                            </Link>
                        </>

                    )}

                    {localStorage.getItem("token") && (

                        <button
                            className="ghost"
                            onClick={() => {
                                localStorage.clear();
                                nav("/login");
                            }}
                        >
                            Logout
                        </button>

                    )}

                </nav>

            </header>

            <main>
                {children}
            </main>
        </>
    );
}


/* =========================
   PROTECTED ROUTE
========================= */

function Protected({ children, admin = false }) {

    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (!token) {
        return <Navigate to="/login" />;
    }

    if (admin && role !== "ADMIN") {
        return <Navigate to="/dashboard" />;
    }

    return (
        <Layout>
            {children}
        </Layout>
    );
}


/* =========================
   LOGIN
========================= */

function Login() {

    const nav = useNavigate();

    const [form, setForm] = useState({
        email: "student1@studentportal.local",
        password: "Student@123"
    });

    const [err, setErr] = useState("");


    async function go(e) {

        e.preventDefault();

        try {

            const { data } = await api.post(
                "/auth/login",
                form
            );

            saveAuth(data);

            nav(
                data.role === "ADMIN"
                    ? "/admin"
                    : "/dashboard"
            );

        } catch (x) {

            setErr(
                x.response?.data?.message ||
                "Login failed"
            );

        }
    }


    return (
        <Auth
            title="Welcome back"
            onSubmit={go}
            button="Login"
            err={err}
            fields={form}
            setFields={setForm}
            footer={
                <Link to="/register">
                    Create student account
                </Link>
            }
        />
    );
}


/* =========================
   REGISTER
========================= */

function Register() {

    const nav = useNavigate();

    const [form, setForm] = useState({
        fullName: "",
        email: "",
        password: ""
    });

    const [err, setErr] = useState("");


    async function go(e) {

        e.preventDefault();

        try {

            const { data } = await api.post(
                "/auth/register",
                form
            );

            saveAuth(data);

            nav("/dashboard");

        } catch (x) {

            setErr(
                x.response?.data?.message ||
                "Registration failed"
            );

        }
    }


    return (
        <Auth
            title="Create student account"
            onSubmit={go}
            button="Register"
            err={err}
            fields={form}
            setFields={setForm}
            register
            footer={
                <Link to="/login">
                    Already have an account?
                </Link>
            }
        />
    );
}


/* =========================
   AUTH COMPONENT
========================= */

function Auth(p) {

    return (

        <div className="auth">

            <div className="panel">

                <span className="eyebrow">
                    STUDENT SKILL & INTERNSHIP PORTAL
                </span>

                <h1>
                    {p.title}
                </h1>

                {p.err && (
                    <div className="error">
                        {p.err}
                    </div>
                )}

                <form onSubmit={p.onSubmit}>

                    {p.register && (

                        <input
                            placeholder="Full name"
                            required
                            value={p.fields.fullName}
                            onChange={(e) =>
                                p.setFields({
                                    ...p.fields,
                                    fullName: e.target.value
                                })
                            }
                        />

                    )}

                    <input
                        type="email"
                        placeholder="Email"
                        required
                        value={p.fields.email}
                        onChange={(e) =>
                            p.setFields({
                                ...p.fields,
                                email: e.target.value
                            })
                        }
                    />

                    <input
                        type="password"
                        placeholder="Password (8+ chars)"
                        minLength="8"
                        required
                        value={p.fields.password}
                        onChange={(e) =>
                            p.setFields({
                                ...p.fields,
                                password: e.target.value
                            })
                        }
                    />

                    <button>
                        {p.button}
                    </button>

                </form>

                <p>
                    {p.footer}
                </p>

            </div>

        </div>
    );
}


/* =========================
   DASHBOARD
========================= */

function Dashboard() {

    const [profile, setProfile] = useState(null);

    const [error, setError] = useState("");


    useEffect(() => {

        async function loadProfile() {

            try {

                const response =
                    await api.get("/students/profile");

                setProfile(response.data);

            } catch (error) {

                console.error(
                    "Dashboard profile error:",
                    error
                );

                setError(
                    "Unable to load profile information."
                );
            }
        }

        loadProfile();

    }, []);


    return (

        <section>

            <div className="hero">

                <div>

                    <span className="eyebrow">
                        STUDENT DASHBOARD
                    </span>

                    <h1>
                        Build skills. Find opportunities.
                    </h1>

                    <p>
                        Keep your profile current and
                        discover internships that match
                        your goals.
                    </p>

                </div>

                <Link
                    className="button"
                    to="/internships"
                >
                    Explore internships
                </Link>

            </div>


            {error && (
                <div className="error">
                    {error}
                </div>
            )}


            {profile && (

                <div className="grid">

                    <div className="card">

                        <h3>
                            Profile completeness
                        </h3>

                        <strong className="big">
                            {profile.profileCompleteness}%
                        </strong>

                        <p>
                            Complete your profile to present
                            a stronger application.
                        </p>

                        <Link to="/profile">
                            Update profile →
                        </Link>

                    </div>


                    <div className="card">

                        <h3>
                            Skills
                        </h3>

                        <p>
                            Manage your technical skill set.
                        </p>

                        <Link to="/skills">
                            Manage skills →
                        </Link>

                    </div>


                    <div className="card">

                        <h3>
                            Applications
                        </h3>

                        <p>
                            Track every internship application
                            and its current status.
                        </p>

                        <Link to="/applications">
                            View applications →
                        </Link>

                    </div>

                </div>

            )}

        </section>
    );
}


/* =========================
   PROFILE
========================= */

function Profile() {

    const [form, setForm] = useState(null);

    const [msg, setMsg] = useState("");

    const [error, setError] = useState("");


    useEffect(() => {

        async function loadProfile() {

            try {

                const response =
                    await api.get("/students/profile");

                setForm(response.data);

            } catch (error) {

                console.error(
                    "Profile API error:",
                    error
                );

                setError(
                    error.response?.data?.message ||
                    "Unable to load profile."
                );
            }
        }

        loadProfile();

    }, []);


    if (error) {

        return (

            <section className="narrow">

                <h1>
                    My Profile
                </h1>

                <div className="error">
                    {error}
                </div>

            </section>
        );
    }


    if (!form) {

        return (
            <section className="narrow">
                <p>
                    Loading profile...
                </p>
            </section>
        );
    }


    const fields = [
        "fullName",
        "email",
        "phone",
        "college",
        "degree",
        "branch",
        "yearOfStudy",
        "cgpa",
        "location",
        "githubUrl",
        "linkedinUrl",
        "portfolioUrl",
        "resumeUrl"
    ];


    return (

        <section className="narrow">

            <h1>
                My Profile
            </h1>


            <form
                className="formgrid"
                onSubmit={async (e) => {

                    e.preventDefault();

                    try {

                        const { data } =
                            await api.put(
                                "/students/profile",
                                form
                            );

                        setForm(data);

                        setMsg(
                            "Profile saved."
                        );

                    } catch (x) {

                        setMsg(
                            x.response?.data?.message ||
                            "Could not save"
                        );

                    }

                }}
            >

                {fields.map((k) => (

                    <label key={k}>

                        {k.replace(
                            /([A-Z])/g,
                            " $1"
                        )}

                        <input
                            type={k === "email" ? "email" : k === "phone" ? "tel" : k === "yearOfStudy" || k === "cgpa" ? "number" : "text"}
                            min={k === "yearOfStudy" ? 1 : k === "cgpa" ? 0 : undefined}
                            max={k === "yearOfStudy" ? 8 : k === "cgpa" ? 10 : undefined}
                            step={k === "cgpa" ? "0.01" : undefined}
                            readOnly={k === "email"}
                            value={form[k] ?? ""}
                            onChange={(e) => setForm({ ...form, [k]: e.target.value })}
                        />

                    </label>

                ))}


                <button>
                    Save profile
                </button>

            </form>


            {msg && (
                <p className="success">
                    {msg}
                </p>
            )}

        </section>
    );
}


/* =========================
   SKILLS
========================= */

function Skills() {

    const [skills, setSkills] = useState([]);

    const [name, setName] = useState("");

    const [msg, setMsg] = useState("");

    const [error, setError] = useState("");


    const load = async () => {

        try {

            const response =
                await api.get("/students/skills");

            setSkills(response.data);

        } catch (error) {

            console.error(
                "Skills API error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Unable to load skills."
            );
        }
    };


    useEffect(() => {

        load();

    }, []);


    return (

        <section className="narrow">

            <h1>
                Skills
            </h1>


            <div className="inline">

                <input
                    placeholder="e.g. Java"
                    value={name}
                    onChange={(e) =>
                        setName(e.target.value)
                    }
                />


                <button
                    onClick={async () => {

                        if (!name.trim()) {
                            setMsg("Skill name is required.");
                            return;
                        }

                        try {

                            await api.post(
                                "/students/skills",
                                { name: name.trim() }
                            );

                            setName("");

                            setMsg("");

                            await load();

                        } catch (x) {

                            setMsg(
                                x.response?.data?.message ||
                                "Could not add"
                            );

                        }

                    }}
                >
                    Add skill
                </button>

            </div>


            {error && (
                <div className="error">
                    {error}
                </div>
            )}


            {msg && (
                <div className="error">
                    {msg}
                </div>
            )}


            <div className="chips">

                {skills.map((s) => (

                    <span
                        key={s.id}
                        className="chip"
                    >

                        {s.name}

                        <button
                            onClick={async () => {

                                try {

                                    await api.delete(
                                        `/students/skills/${s.id}`
                                    );

                                    await load();

                                } catch (error) {

                                    console.error(
                                        "Delete skill error:",
                                        error
                                    );
                                }

                            }}
                        >
                            ×
                        </button>

                    </span>

                ))}

            </div>

        </section>
    );
}


/* =========================
   INTERNSHIPS
========================= */

function Internships() {

    const [data, setData] = useState([]);

    const [q, setQ] = useState("");

    const [mode, setMode] = useState("");

    const [error, setError] = useState("");


    const load = async () => {

        try {

            setError("");

            const response =
                await api.get(
                    "/internships",
                    {
                        params: {
                            q,
                            workMode:
                                mode || undefined
                        }
                    }
                );

            setData(response.data);

        } catch (error) {

            console.error(
                "Internships API error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Unable to load internships."
            );
        }
    };


    useEffect(() => {

        load();

    }, []);


    return (

        <section>

            <div className="toolbar">

                <div>

                    <h1>
                        Internships
                    </h1>

                    <p>
                        Search opportunities using
                        backend-powered filters.
                    </p>

                </div>


                <div className="filters">

                    <input
                        placeholder="Title, company or skill"
                        value={q}
                        onChange={(e) =>
                            setQ(e.target.value)
                        }
                    />


                    <select
                        value={mode}
                        onChange={(e) =>
                            setMode(e.target.value)
                        }
                    >

                        <option value="">
                            All modes
                        </option>

                        <option value="REMOTE">
                            REMOTE
                        </option>

                        <option value="HYBRID">
                            HYBRID
                        </option>

                        <option value="ONSITE">
                            ONSITE
                        </option>

                    </select>


                    <button onClick={load}>
                        Search
                    </button>

                </div>

            </div>


            {error && (

                <div className="error">
                    {error}
                </div>

            )}


            <div className="cards">

                {data.map((i) => (

                    <Link
                        className="intern"
                        to={`/internships/${i.id}`}
                        key={i.id}
                    >

                        <div className="tag">
                            {i.workMode}
                        </div>

                        <h3>
                            {i.title}
                        </h3>

                        <b>
                            {i.companyName}
                        </b>

                        <p>
                            {i.location} · {i.duration}
                        </p>

                        <span>
                            ₹{i.stipend ?? 0} stipend
                        </span>

                    </Link>

                ))}

            </div>


            {!error && data.length === 0 && (

                <p>
                    No internships found.
                </p>

            )}

        </section>
    );
}


/* =========================
   INTERNSHIP DETAILS
========================= */

function InternshipDetails() {

    const { id } = useParams();

    const [i, setI] = useState(null);

    const [msg, setMsg] = useState("");

    const [error, setError] = useState("");


    useEffect(() => {

        async function loadInternship() {

            try {

                const response =
                    await api.get(
                        `/internships/${id}`
                    );

                setI(response.data);

            } catch (error) {

                console.error(
                    "Internship details error:",
                    error
                );

                setError(
                    error.response?.data?.message ||
                    "Unable to load internship."
                );
            }
        }

        loadInternship();

    }, [id]);


    if (error) {

        return (

            <section className="narrow">

                <h1>
                    Internship
                </h1>

                <div className="error">
                    {error}
                </div>

            </section>
        );
    }


    if (!i) {

        return (
            <section className="narrow">
                <p>
                    Loading...
                </p>
            </section>
        );
    }


    return (

        <section className="narrow">

            <div className="tag">
                {i.workMode}
            </div>

            <h1>
                {i.title}
            </h1>

            <h2>
                {i.companyName}
            </h2>


            <div className="details">

                <p>
                    <b>Location:</b>{" "}
                    {i.location}
                </p>

                <p>
                    <b>Duration:</b>{" "}
                    {i.duration}
                </p>

                <p>
                    <b>Stipend:</b>{" "}
                    ₹{i.stipend ?? 0}
                </p>

                <p>
                    <b>Deadline:</b>{" "}
                    {i.applicationDeadline}
                </p>

                <p>
                    <b>Eligibility:</b>{" "}
                    {i.eligibility}
                </p>

                <p>
                    <b>Skills:</b>{" "}
                    {i.requiredSkills}
                </p>

            </div>


            <p>
                {i.description}
            </p>


            {msg && (
                <div className="success">
                    {msg}
                </div>
            )}


            <button
                disabled={i.status !== "OPEN"}
                onClick={async () => {

                    try {

                        await api.post(
                            "/applications",
                            {
                                internshipId: i.id,
                                coverLetter:
                                    "I am interested in this internship and would be glad to contribute."
                            }
                        );

                        setMsg(
                            "Application submitted successfully."
                        );

                    } catch (x) {

                        setMsg(
                            x.response?.data?.message ||
                            "Application failed"
                        );

                    }

                }}
            >

                {i.status === "OPEN"
                    ? "Apply now"
                    : "Closed"}

            </button>

        </section>
    );
}


/* =========================
   APPLICATIONS
========================= */

function Applications() {

    const [a, setA] = useState([]);

    const [error, setError] = useState("");


    useEffect(() => {

        async function loadApplications() {

            try {

                const response =
                    await api.get(
                        "/applications/my"
                    );

                setA(response.data);

            } catch (error) {

                console.error(
                    "Applications API error:",
                    error
                );

                setError(
                    error.response?.data?.message ||
                    "Unable to load applications."
                );
            }
        }

        loadApplications();

    }, []);


    return (

        <section>

            <h1>
                My Applications
            </h1>


            {error && (
                <div className="error">
                    {error}
                </div>
            )}


            <div className="tablewrap">

                <table>

                    <thead>

                        <tr>
                            <th>Company</th>
                            <th>Internship</th>
                            <th>Date</th>
                            <th>Status</th>
                        </tr>

                    </thead>


                    <tbody>

                        {a.map((x) => (

                            <tr key={x.id}>

                                <td>
                                    {x.companyName}
                                </td>

                                <td>
                                    {x.internshipTitle}
                                </td>

                                <td>
                                    {new Date(
                                        x.applicationDate
                                    ).toLocaleDateString()}
                                </td>

                                <td>

                                    <span
                                        className={`status ${x.status.toLowerCase()}`}
                                    >
                                        {x.status}
                                    </span>

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            </div>


            {!error && a.length === 0 && (
                <p>
                    No applications found.
                </p>
            )}

        </section>
    );
}


/* =========================
   ADMIN
========================= */

function Admin() {

    const [stats, setStats] = useState(null);

    const [apps, setApps] = useState([]);

    const [students, setStudents] = useState([]);

    const [internships, setInternships] = useState([]);

    const [form, setForm] = useState({

        companyName: "",
        title: "",
        description: "",
        requiredSkills: "",
        location: "",
        workMode: "REMOTE",
        duration: "3 Months",
        stipend: 10000,
        startDate: "",
        applicationDeadline: "",
        eligibility: "B.Tech students",
        openings: 1,
        status: "OPEN"

    });

    const [msg, setMsg] = useState("");


    const load = async () => {

        try {

            const [
                statsResponse,
                appsResponse,
                studentsResponse,
                internshipsResponse
            ] = await Promise.all([

                api.get("/admin/stats"),

                api.get("/admin/applications"),

                api.get("/admin/students"),

                api.get("/admin/internships")

            ]);


            setStats(statsResponse.data);

            setApps(appsResponse.data);

            setStudents(studentsResponse.data);

            setInternships(
                internshipsResponse.data
            );

        } catch (error) {

            console.error(
                "Admin API error:",
                error
            );

        }
    };


    useEffect(() => {

        load();

    }, []);


    const create = async (e) => {

        e.preventDefault();

        try {

            await api.post(
                "/admin/internships",
                form
            );

            setMsg(
                "Internship created."
            );

            await load();

        } catch (x) {

            setMsg(
                x.response?.data?.message ||
                "Could not create internship"
            );
        }
    };


    return (

        <section>

            <span className="eyebrow">
                ADMIN CONTROL CENTER
            </span>

            <h1>
                Platform overview
            </h1>


            {stats && (

                <div className="grid stats">

                    {Object.entries(stats).map(
                        ([k, v]) => (

                            <div
                                className="card"
                                key={k}
                            >

                                <small>
                                    {k.replace(
                                        /([A-Z])/g,
                                        " $1"
                                    )}
                                </small>

                                <strong className="big">
                                    {v}
                                </strong>

                            </div>

                        )
                    )}

                </div>

            )}


            <div className="admincols">

                <div className="card">

                    <h2>
                        Add internship
                    </h2>


                    <form
                        className="formgrid"
                        onSubmit={create}
                    >

                        {[
                            "companyName",
                            "title",
                            "description",
                            "requiredSkills",
                            "location",
                            "duration",
                            "eligibility",
                            "startDate",
                            "applicationDeadline",
                            "stipend",
                            "openings"
                        ].map((k) => (

                            <label key={k}>

                                {k.replace(
                                    /([A-Z])/g,
                                    " $1"
                                )}

                                <input
                                    type={
                                        k.includes("Date") ? "date" : (k === "stipend" || k === "openings" ? "number" : "text")
                                    }
                                    min={k === "stipend" ? 0 : k === "openings" ? 1 : undefined}
                                    required={[
                                        "companyName",
                                        "title",
                                        "description",
                                        "requiredSkills",
                                        "location",
                                        "duration",
                                        "eligibility",
                                        "startDate",
                                        "applicationDeadline"
                                    ].includes(k)}
                                    value={form[k]}
                                    onChange={(e) =>
                                        setForm({
                                            ...form,
                                            [k]: e.target.value
                                        })
                                    }
                                />

                            </label>

                        ))}


                        <label>

                            Work mode

                            <select
                                value={form.workMode}
                                onChange={(e) =>
                                    setForm({
                                        ...form,
                                        workMode:
                                            e.target.value
                                    })
                                }
                            >

                                <option>
                                    REMOTE
                                </option>

                                <option>
                                    HYBRID
                                </option>

                                <option>
                                    ONSITE
                                </option>

                            </select>

                        </label>


                        <button>
                            Create internship
                        </button>

                    </form>


                    {msg && (
                        <p className="success">
                            {msg}
                        </p>
                    )}

                </div>


                <div className="card">

                    <h2>
                        Students ({students.length})
                    </h2>


                    {students.map((x) => (

                        <p key={x.id}>

                            <b>
                                {x.fullName}
                            </b>

                            <br />

                            <small>
                                {x.email} · {x.branch}
                            </small>

                        </p>

                    ))}

                </div>

            </div>


            <h2>
                Internships
            </h2>


            <div className="tablewrap">

                <table>

                    <thead>

                        <tr>
                            <th>Company</th>
                            <th>Title</th>
                            <th>Mode</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>

                    </thead>


                    <tbody>

                        {internships.map((i) => (

                            <tr key={i.id}>

                                <td>
                                    {i.companyName}
                                </td>

                                <td>
                                    {i.title}
                                </td>

                                <td>
                                    {i.workMode}
                                </td>

                                <td>
                                    {i.status}
                                </td>

                                <td>

                                    {i.status === "OPEN" && (

                                        <button
                                            onClick={async () => {
                                                try {
                                                    await api.delete(`/admin/internships/${i.id}`);
                                                    setMsg("Internship closed.");
                                                    await load();
                                                } catch (x) {
                                                    setMsg(x.response?.data?.message || "Could not close internship");
                                                }
                                            }}
                                        >
                                            Close
                                        </button>

                                    )}

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            </div>


            <h2>
                Applications
            </h2>


            <div className="tablewrap">

                <table>

                    <thead>

                        <tr>
                            <th>Student</th>
                            <th>Internship</th>
                            <th>Status</th>
                            <th>Update</th>
                        </tr>

                    </thead>


                    <tbody>

                        {apps.map((a) => (

                            <tr key={a.id}>

                                <td>
                                    {a.studentName}
                                </td>

                                <td>
                                    {a.internshipTitle}
                                </td>

                                <td>
                                    {a.status}
                                </td>

                                <td>

                                    <select
                                        value={a.status}
                                        onChange={async (e) => {
                                            try {
                                                await api.put(
                                                    `/admin/applications/${a.id}/status`,
                                                    { status: e.target.value }
                                                );
                                                await load();
                                            } catch (x) {
                                                setMsg(x.response?.data?.message || "Could not update application status");
                                            }
                                        }}
                                    >

                                        <option>
                                            APPLIED
                                        </option>

                                        <option>
                                            SHORTLISTED
                                        </option>

                                        <option>
                                            SELECTED
                                        </option>

                                        <option>
                                            REJECTED
                                        </option>

                                    </select>

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            </div>

        </section>
    );
}


/* =========================
   APP ROUTES
========================= */

export default function App() {

    return (

        <Routes>

            <Route
                path="/login"
                element={<Login />}
            />

            <Route
                path="/register"
                element={<Register />}
            />

            <Route
                path="/dashboard"
                element={
                    <Protected>
                        <Dashboard />
                    </Protected>
                }
            />

            <Route
                path="/profile"
                element={
                    <Protected>
                        <Profile />
                    </Protected>
                }
            />

            <Route
                path="/skills"
                element={
                    <Protected>
                        <Skills />
                    </Protected>
                }
            />

            <Route
                path="/internships"
                element={
                    <Protected>
                        <Internships />
                    </Protected>
                }
            />

            <Route
                path="/internships/:id"
                element={
                    <Protected>
                        <InternshipDetails />
                    </Protected>
                }
            />

            <Route
                path="/applications"
                element={
                    <Protected>
                        <Applications />
                    </Protected>
                }
            />

            <Route
                path="/admin"
                element={
                    <Protected admin>
                        <Admin />
                    </Protected>
                }
            />

            <Route
                path="*"
                element={
                    <Navigate to="/dashboard" />
                }
            />

        </Routes>
    );
}