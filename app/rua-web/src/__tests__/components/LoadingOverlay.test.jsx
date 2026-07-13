import { describe, it, expect } from "vitest";
import { render } from "@testing-library/react";
import LoadingOverlay from "../../components/LoadingOverlay.jsx";

describe("LoadingOverlay", () => {
  it("renders loading overlay with spinner", () => {
    const { container } = render(<LoadingOverlay />);
    expect(container.querySelector(".loading-overlay")).not.toBeNull();
    expect(container.querySelector(".loading-spinner")).not.toBeNull();
  });

  it("has accessible role and label", () => {
    const { container } = render(<LoadingOverlay />);
    const overlay = container.querySelector(".loading-overlay");
    expect(overlay.getAttribute("role")).toBe("status");
    expect(overlay.getAttribute("aria-label")).toBe("Cargando");
  });
});
