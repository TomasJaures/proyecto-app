import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import MobileHeader from "../../components/MobileHeader.jsx";

describe("MobileHeader", () => {
  it("renders RUA title", () => {
    render(<MobileHeader />);
    expect(screen.getByText("RUA")).toBeInTheDocument();
  });

  it("has the mobile header class", () => {
    const { container } = render(<MobileHeader />);
    expect(container.firstChild).toHaveClass("barra-mobile");
  });
});
