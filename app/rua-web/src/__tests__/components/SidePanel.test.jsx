import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import SidePanel from "../../components/SidePanel.jsx";

describe("SidePanel", () => {
  it("renders the RUA brand title", () => {
    render(<SidePanel>Test text</SidePanel>);
    expect(screen.getByText("RUA")).toBeInTheDocument();
    expect(screen.getByText("Sistema de Asistencia")).toBeInTheDocument();
  });

  it("renders children text", () => {
    render(<SidePanel>Custom description</SidePanel>);
    expect(screen.getByText("Custom description")).toBeInTheDocument();
  });

  it("renders the footer disclaimer", () => {
    render(<SidePanel>Content</SidePanel>);
    expect(screen.getByText("Sitio web no afiliado a la UFRO")).toBeInTheDocument();
  });
});
